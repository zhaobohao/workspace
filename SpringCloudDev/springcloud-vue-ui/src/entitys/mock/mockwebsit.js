                export default function mockwebsite() {
                  return {
                    current: 1,
                    size: 20,
                    isNewRecord: false,
                    query: {
                      // 表id
                      id: undefined,
                      // 站点名称
                      name: undefined,
                      // 远端的访问地址，带端口号。例如：http://xxxx.yyy.com:9090/uri
                      addressUrl: undefined,
                      name_like: '',
                      addressUrl_like: ''
                    },
                    rules: {
                      id: [{
                        required: true,
                        message: '请输入表id',
                        trigger: 'blur'
                      }],
                      name: [{
                        required: true,
                        message: '请输入站点名称,最大长度为128',
                        max: 128,
                        trigger: 'blur'
                      }],
                      addressUrl: [{
                        required: true,
                        max: 255,
                        message: '最大长度为255请输入远端的访问地址，带端口号。例如：http://xxxx.yyy.com:9090/uri',
                        trigger: 'blur'
                      }]
                    }
                  }
                }
