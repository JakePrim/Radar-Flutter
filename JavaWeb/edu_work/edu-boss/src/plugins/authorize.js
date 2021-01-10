/**
 * Check login state
 * Some middleware to help us ensure the user is authenticated.
 */

import router from '../router'
import store from '../store'

export default Vue => {
  // Authorize (Make sure that is the first hook.)
  router.beforeHooks.unshift((to, from, next) => {
    // don't need authorize
    if (!to.meta.requireAuth) return next()
    // check login state
    store.dispatch('checkToken')
      .then(valid => {
        // authorized
        if (valid) return next()
        // unauthorized
        console.log('Unauthorized')
        next({ name: 'Login', query: { redirect: to.fullPath } })
      })
  })

  // login page visiable
  router.beforeEach((to, from, next) => {
    if (to.name !== 'Login') return next()
    // check login state
    store.dispatch('checkToken')
      .then(valid => {
        if (!valid) return next()
        // when logged in
        console.log('Authorized')
        next({ path: to.query.redirect || '/' })
      })
  })
}
