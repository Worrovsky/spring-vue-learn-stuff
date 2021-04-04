import userProfileApi from '@/api/userProfile'

const state = {
  isLoading: false,
  data: null,
  error: null,
}

const preffix = '[userProfile]'

export const mutationTypes = {
  getUserProfileStart: `${preffix} Get user profile start`,
  getUserProfileSuccess: `${preffix} Get user profile success`,
  getUserProfileFailure: `${preffix} Get user profile failure`,
}

const mutations = {
  [mutationTypes.getUserProfileStart](state) {
    state.isLoading = true
    state.data = null
    state.error = null
  },
  [mutationTypes.getUserProfileSuccess](state, payload) {
    state.isLoading = false
    state.data = payload
    state.error = false
  },
  [mutationTypes.getUserProfileFailure](state) {
    state.isLoading = false
    state.data = null
    state.error = null
  },
}

export const actionTypes = {
  getUserProfile: `${preffix} Get user profile`,
}

const actions = {
  [actionTypes.getUserProfile](context, { slug }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.getUserProfileStart)
      userProfileApi
        .getUserProfile(slug)
        .then((userProfile) => {
          context.commit(mutationTypes.getUserProfileSuccess, userProfile)
          resolve(userProfile)
        })
        .catch(() => {
          context.commit(mutationTypes.getUserProfileFailure)
        })
    })
  },
}

export default {
  state,
  mutations,
  actions,
}
