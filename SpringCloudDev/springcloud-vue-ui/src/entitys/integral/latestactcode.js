            export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 活动codeid
  actCodeId: undefined,
      // 活动编号前缀
  prefixActCode: undefined,
      // 活动编号后缀
  suffixActCode: undefined,
          },
    rules: {
        actCodeId: [{
  required: true,
  message: '请输入活动codeid',
  trigger: 'blur'
  }],
          prefixActCode: [{
  required: true,
  message: '请输入活动编号前缀',
  trigger: 'blur'
  }],
          suffixActCode: [{
  required: true,
  message: '请输入活动编号后缀',
  trigger: 'blur'
  }],
      }
  }
}
