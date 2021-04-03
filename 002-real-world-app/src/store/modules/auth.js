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
  registerStart: `${preffix} Register Start`,
  registerFailure: `${preffix} Register Failure`,
  registerSuccess: `${preffix} Register Success`,

  getCurrentUserStart: `${preffix} Get current user Start`,
  getCurrentUserFailure: `${preffix} Get current user Failure`,
  getCurrentUserSuccess: `${preffix} Get current user Success`,

  updateCurrentUserStart: `${preffix} Update current user Start`,
  updateCurrentUserFailure: `${preffix} Update current user Failure`,
  updateCurrentUserSuccess: `${preffix} Update current user Success`,

  logout: `${preffix} Logout`,
}

export const actionTypes = {
  register: `${preffix} register`,
  signin: `${preffix} signin`,
  getCurrentUser: `${preffix} getCurrentUser`,
  updateCurrentUser: `${preffix} updateCurrentUser`,
  logout: `${preffix} logout`,
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
  [mutationTypes.updateCurrentUserStart]() {},
  [mutationTypes.updateCurrentUserSuccess](state, payload) {
    state.currentUser = payload
  },
  [mutationTypes.updateCurrentUserFailure]() {},

  [mutationTypes.logout](state) {
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

  [actionTypes.updateCurrentUser](context, { currentUserInput }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.updateCurrentUserStart)
      authApi
        .updateCurrentUser(currentUserInput)
        .then((user) => {
          context.commit(mutationTypes.updateCurrentUserSuccess, user)
          resolve(user)
        })
        .catch((result) => {
          context.commit(
            mutationTypes.updateCurrentUserFailure,
            result.response.data.errors
          )
        })
    })
  },

  [actionTypes.logout](context) {
    return new Promise((resolve) => {
      setItem('accessToken', '')
      context.commit(mutationTypes.logout)
      resolve()
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
