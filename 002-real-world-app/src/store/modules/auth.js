import authApi from '@/api/auth'
import { setItem } from '@/utils/storageUtils'

const state = {
  isSubmitting: false,
  currentUser: null,
  validationErrors: null,
  isLoggedIn: null, // 3-state: logged in, not logged in, unknowne
}

export const mutationTypes = {
  registerStart: '[auth] registerStart',
  registerFailure: '[auth] registerFailure',
  registerSuccess: '[auth] registerSuccess',
}

export const actionTypes = {
  register: '[auth] register',
}

const mutations = {
  [mutationTypes.registerStart](state) {
    state.isSubmitting = true
    state.validationErrors = null
  },

  [mutationTypes.registerSuccess](state, payload) {
    state.isSubmitting = false
    state.currentUser = payload
    state.isLoggedIn = true
    state.validationErrors = null
  },

  [mutationTypes.registerFailure](state, errors) {
    state.isSubmitting = false
    state.currentUser = null
    state.isLoggedIn = false
    state.validationErrors = errors
  },
}

const actions = {
  [actionTypes.register](context, credentials) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.registerStart)
      authApi
        .register(credentials)
        .then((response) => {
          context.commit(mutationTypes.registerSuccess, response.data.user)
          setItem('accessToken', response.data.user.token)
          resolve(response.data.user)
        })
        .catch((result) => {
          console.log('result: ', result)
          context.commit(
            mutationTypes.registerFailure,
            result.response.data.errors
          )
        })
    })
  },
}

export default {
  state,
  mutations,
  actions,
}
