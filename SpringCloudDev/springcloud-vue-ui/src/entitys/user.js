import {
  formatDate
} from '@/utils/formatDate'
export default function user() {
  return {
    id: undefined,
    pageNo: 1,
    pageSize: 20,
    isNewRecord: false,
    createDate: formatDate(new Date(), 'yyyy-MM-dd hh:mm:ss'),
    updateDate: formatDate(new Date(), 'yyyy-MM-dd hh:mm:ss'),
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
    birthday: undefined,
    // 账号状态
    statusName: undefined

  }
}
