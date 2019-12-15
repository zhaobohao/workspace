 export default function user() {
   return {
     id: undefined,
     pageNo: 1,
     pageSize: 20,
     isNewRecord: false,
     query: {
       id: undefined,
       // 登录账号
       account: undefined,
       // 所属租户
       tenantId: undefined,
       // 密码
       password: undefined,
       // 确认密码
       password2: undefined,
       // 用户昵称
       name: undefined,
       // 用户姓名
       realName: undefined,
       // 所属角色
       roleId: undefined,
       // 所属部门
       deptId: undefined,
       // 手机号码
       phone: undefined,
       // 电子邮箱
       email: undefined,
       // 用户性别
       sex: undefined,
       // 用户生日
       birthday: undefined
     },
     rules: {
       account: [{
         required: true,
         message: '请输入登录账号',
         trigger: 'blur'
       }],
       tenantId: [{
         required: true,
         message: '请输入所属租户',
         trigger: 'click'
       }],
       password: [{
         required: true,
         // validator: validatePass,
         trigger: 'blur'
       }],
       password2: [{
         required: true,
         // validator: validatePass2,
         trigger: 'blur'
       }],
       name: [{
         required: true,
         message: '请输入用户昵称',
         trigger: 'blur'
       }],
       realName: [{
         required: true,
         message: '请输入用户姓名',
         trigger: 'blur'
       }],
       roleId: [{
         required: true,
         message: '请选择所属角色',
         trigger: 'click'
       }]

     }
   }
 }
