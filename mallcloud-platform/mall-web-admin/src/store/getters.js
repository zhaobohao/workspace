const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  storeName: state => state.user.storeName,
  name: state => state.user.name,
  roles: state => state.user.roles,
  menus: state => state.user.menus
}
export default getters
