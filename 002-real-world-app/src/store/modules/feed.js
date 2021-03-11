import feedApi from '@/api/feed'

const state = {
  isLoading: false,
  data: null,
  error: null,
}

const preffix = '[feed]'

export const mutationTypes = {
  getFeedStart: `${preffix} Get feed start`,
  getFeedSuccess: `${preffix} Get feed success`,
  getFeedFailure: `${preffix} Get feed failure`,
}

const mutations = {
  [mutationTypes.getFeedStart](state) {
    state.isLoading = true
    state.data = null
    state.error = null
  },
  [mutationTypes.getFeedSuccess](state, payload) {
    state.isLoading = false
    state.data = payload
    state.error = false
  },
  [mutationTypes.getFeedFailure](state) {
    state.isLoading = false
    state.data = null
    state.error = null
  },
}

export const actionTypes = {
  getFeed: `${preffix} Get feed`,
}

const actions = {
  [actionTypes.getFeed](context, { apiUrl }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.getFeedStart)
      feedApi
        .getFeed(apiUrl)
        .then((response) => {
          context.commit(mutationTypes.getFeedSuccess, response.data)
          resolve(response.data)
        })
        .catch(() => {
          context.commit(mutationTypes.getFeedFailure)
        })
    })
  },
}

export default {
  state,
  mutations,
  actions,
}
