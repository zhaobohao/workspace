export default {
  success(Vue, config = {}) {
    Vue.$message({
      title: config.title || Vue.$t('notify.successTitle'),
      message: config.message || Vue.$t('notify.successMessage'),
      type: 'success',
      duration: 2000
    })
  },
  error(Vue, config = {}) {
    Vue.$message({
      title: config.title || Vue.$t('notify.failTitle'),
      message: config.message || Vue.$t('notify.failMessage'),
      type: 'error',
      duration: 2000
    })
  },
  info(Vue, config) {
    Vue.$message({
      title: config.title,
      message: config.message,
      type: 'info',
      duration: 2000
    })
  },
  warning(Vue, config) {
    Vue.$message({
      title: config.title,
      message: config.message,
      type: 'warning',
      duration: 2000
    })
  },
  successEdit(Vue, config = {}) {
    this.success(Vue, {
      title: Vue.$t('notify.successEditTitle'),
      message: Vue.$t('notify.successEditMessage')
    })
  },
  errorEdit(Vue, config = {}) {
    this.error(Vue, {
      title: Vue.$t('notify.failEditTitle'),
      message: Vue.$t('notify.failEditMessage')
    })
  }
}
