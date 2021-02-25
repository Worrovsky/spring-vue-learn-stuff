import authApi from '@/api/auth'
import { setItem } from '@/utils/storageUtils'

const preffix = '[auth]'

const state = {
  isSubmitting: false,
  currentUser: null,
  validationErrors: null,
  isLoggedIn: null, // 3-state: logged in, not logged in, unknowne
  isLoading: false,
}

export const mutationTypes = {
  registerStart: `${preffix} registerStart`,
  registerFailure: `${preffix} registerFailure`,
  registerSuccess: `${preffix} registerSuccess`,

  getCurrentUserStart: `${preffix} registerStart`,
  getCurrentUserFailure: `${preffix} registerFailure`,
  getCurrentUserSuccess: `${preffix} registerSuccess`,
}

export const actionTypes = {
  register: `${preffix} register`,
  signin: `${preffix} signin`,
  getCurrentUser: `${preffix} getCurrentUser`,
}

export const getterTypes = {
  currentUser: `${preffix} currentUser`,
  isLoggedIn: `${preffix} isLoggedIn`,
  isAnonimous: `${preffix} isAnonimous`,
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

  [mutationTypes.getCurrentUserStart](state) {
    state.isLoading = true
  },
  [mutationTypes.getCurrentUserSuccess](state, user) {
    state.isLoading = false
    state.currentUser = user
    state.isLoggedIn = true
  },
  [mutationTypes.getCurrentUserFailure](state) {
    state.isLoading = false
    state.currentUser = null
    state.isLoggedIn = false
  },
}

const runAuthMethod = (method, context, credentials) => {
  return new Promise((resolve) => {
    context.commit(mutationTypes.registerStart)
    method(credentials)
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
}

const actions = {
  [actionTypes.register](context, credentials) {
    return runAuthMethod(authApi.register, context, credentials)
  },
  [actionTypes.signin](context, credentials) {
    return runAuthMethod(authApi.signin, context, credentials)
  },

  [actionTypes.getCurrentUser](context) {
    return new Promise(() => {
      context.commit(mutationTypes.getCurrentUserStart)
      authApi
        .getCurrentUser()
        .then((response) => {
          context.commit(
            mutationTypes.getCurrentUserSuccess,
            response.data.user
          )
        })
        .catch(() => {
          context.commit(mutationTypes.getCurrentUserFailure)
        })
    })
  },
}

const getters = {
  [getterTypes.currentUser]: (state) => {
    return state.currentUser
  },
  [getterTypes.isLoggedIn]: (state) => {
    return Boolean(state.isLoggedIn)
  },
  [getterTypes.isAnonimous]: (state) => {
    return state.isLoggedIn === false
  },
}

export default {
  state,
  mutations,
  actions,
  getters,
}
