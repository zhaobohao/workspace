using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Timers;
using System.Runtime.CompilerServices;

using EasyopenSDKCSharp.Request;
using EasyopenSDKCSharp.Response;
using EasyopenSDKCSharp.Common;
using EasyopenSDKCSharp.Utility;

namespace EasyopenSDKCSharp.Client
{
    /// <summary>
    /// 加密请求客户端
    /// </summary>
    public class EncryptClient : OpenClient
    {

        private int holdSessionPeriodSeconds = 60 * 10;
        /// <summary>
        /// 保持session不断，间隔执行Timer任务秒数，默认10秒
        /// </summary>
        public int HoldSessionPeriodSeconds
        {
            get { return holdSessionPeriodSeconds; }
            set { holdSessionPeriodSeconds = value; }
        }

        private string publicKey;
        /// <summary>
        /// 公钥
        /// </summary>
        public string PublicKey
        {
            get { return publicKey; }
            set { publicKey = value; }
        }

        // 随机码
        private volatile string randomKey;
        // 定时器
        private Timer timer;

        private string handshakeUrl;

        public string HandshakeUrl
        {
            get { return handshakeUrl; }
            set { handshakeUrl = value; }
        }

        private EncryptClient(string url, string appKey, string secret)
            : base(url, appKey, secret)
        {            
        }

        public EncryptClient(string url, string appKey, string secret, string publicKey)
            : base(url + "/ssl2", appKey, secret)
        {


            this.handshakeUrl = url + "/handshake2";
            this.publicKey = publicKey;

            this.handshake();

            this.initTimer();
        }

        /// <summary>
        /// 握手操作，目的是与服务器交换随机码
        /// </summary>
        [MethodImplAttribute(MethodImplOptions.Synchronized)]
        protected virtual void handshake()
        {
            try
            {
                string randomKey = this.createRandomKey();
                // 传递随机数
                HandshakeParam param = this.createHandShakeParam(randomKey);
                string json = this.OpenRequest.PostJsonBody(this.handshakeUrl, JsonUtil.ToJSONString(param));
                checkHandshakeResponse(json, randomKey);
                this.randomKey = randomKey;
            }
            catch (Exception e)
            {
                throw new SystemException(string.Format("握手失败:{0}", e.Message));
            }            
        }

        protected virtual HandshakeParam createHandShakeParam(string randomKey)
        {
            HandshakeParam param = new HandshakeParam();
            string randomKeyEncrypted = RSAUtil.EncryptByPublicKey(randomKey, publicKey);
            param.app_key = this.AppKey;
            param.data = randomKeyEncrypted;
            return param;
        }

        protected virtual void checkHandshakeResponse(string resp, string randomKey)
        {
            StringResponse result = JsonUtil.ParseObject<StringResponse>(resp);
            if (!result.IsSuccess())
            {
                throw new SystemException(result.msg);
            }

            string data = result.data;
            string desStr = RSAUtil.DecryptByPublicKey(data, publicKey);

            string content = AESUtil.DecryptFromBase64String(desStr, randomKey);
            // 一致
            bool same = MD5Util.Encrypt(randomKey) == content;

            if (!same)
            {
                throw new SystemException("传输错误");
            }
        }

        /// <summary>
        /// 创建随机码
        /// </summary>
        /// <returns></returns>
        protected virtual string createRandomKey()
        {
            return MD5Util.Encrypt16(Guid.NewGuid().ToString());
        }

        [MethodImplAttribute(MethodImplOptions.Synchronized)]
        protected override string doExecute(string url, RequestForm requestForm, Dictionary<string, string> header)
        {
            Dictionary<string, object> form = requestForm.Form;
            this.encryptData(form);
            this.encryptHeader(header);
            return base.doExecute(url, requestForm, header);
        }

        protected override T parseResponse<T>(string resp, BaseRequest<T> request)
        {
            try {
                resp = AESUtil.DecryptFromBase64String(resp, this.randomKey);
                return base.parseResponse<T>(resp, request);
            } catch (Exception e) {
                throw new Exception("AES解密失败", e);
            }
        }

        private void encryptData(Dictionary<string, object> form)
        {
            string dataName = this.OpenConfig.DataName;
            string data = form[dataName].ToString();
            data = AESUtil.EncryptToBase64String(data, randomKey);
            form[dataName] = data;
        }

        // 加密header
        private void encryptHeader(Dictionary<string, string> header)
        {
            ICollection<string> keys = header.Keys;

            for (int i = 0; i < keys.Count; i++)
            {
                string key = keys.ElementAt(i);
                string value = header[key];
                value = AESUtil.EncryptToBase64String(value, randomKey);
                header[key] = value;
            }
        }

        /// <summary>
        /// 初始化定时器
        /// </summary>
        protected virtual void initTimer() {
            Timer t = new Timer();
            t.Elapsed += new ElapsedEventHandler(OnTimedEvent);
            t.Interval = 1000 * this.holdSessionPeriodSeconds;
            t.Enabled = true;
            this.timer = t;
        }

        /// <summary>
        /// 触发定时器任务
        /// </summary>
        /// <param name="source"></param>
        /// <param name="e"></param>
        protected virtual void OnTimedEvent(object source, ElapsedEventArgs e)
        {
            this.handshake();
        }

    }

    class StringResponse : BaseResponse<string>
    {
    }
}
