import {
  formatDate
} from '@/utils/date'
export default function param() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 通知标题
      title: undefined,
      // 通知类型
      category: undefined,
      // 通知日期
      releaseTime: formatDate(new Date(), 'yyyy-MM-dd hh:mm:ss'),
      // 通知内容
      content: undefined
    },
    rules: {
      title: [{
        required: true,
        message: '请输入通知标题',
        trigger: 'blur'
      }],
      category: [{
        required: true,
        message: '请输入通知类型',
        trigger: 'blur'
      }],
      releaseTime: [{
        required: true,
        message: '请输入通知日期',
        trigger: 'blur'
      }]
    }
  }
}
