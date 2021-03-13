import popularTagsApi from '@/api/popularTags'

const state = {
  isLoading: false,
  data: null,
  error: null,
}

const preffix = '[popularTags]'

export const mutationTypes = {
  getPopularTagsStart: `${preffix} Get popular tags start`,
  getPopularTagsSuccess: `${preffix} Get popular tags success`,
  getPopularTagsFailure: `${preffix} Get popular tags failure`,
}

const mutations = {
  [mutationTypes.getPopularTagsStart](state) {
    state.isLoading = true
    state.data = null
    state.error = null
  },
  [mutationTypes.getPopularTagsSuccess](state, payload) {
    state.isLoading = false
    state.data = payload
    state.error = false
  },
  [mutationTypes.getPopularTagsFailure](state) {
    state.isLoading = false
    state.data = null
    state.error = null
  },
}

export const actionTypes = {
  getPopularTags: `${preffix} Get popular tags`,
}

const actions = {
  [actionTypes.getPopularTags](context) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.getPopularTagsStart)
      popularTagsApi
        .getPopularTags()
        .then((tags) => {
          context.commit(mutationTypes.getPopularTagsSuccess, tags)
          resolve(tags)
        })
        .catch(() => {
          context.commit(mutationTypes.getPopularTagsFailure)
        })
    })
  },
}

export default {
  state,
  mutations,
  actions,
}
