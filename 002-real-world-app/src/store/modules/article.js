import articleApi from '@/api/article'

const state = {
  isLoading: false,
  data: null,
  error: null,
}

const preffix = '[article]'

export const mutationTypes = {
  getArticleStart: `${preffix} Get article start`,
  getArticleSuccess: `${preffix} Get article success`,
  getArticleFailure: `${preffix} Get article failure`,

  deleteArticleStart: `${preffix} Delete article start`,
  deleteArticleSuccess: `${preffix} Delete article success`,
  deleteArticleFailure: `${preffix} Delete article failure`,
}

const mutations = {
  [mutationTypes.getArticleStart](state) {
    state.isLoading = true
    state.data = null
    state.error = null
  },
  [mutationTypes.getArticleSuccess](state, payload) {
    state.isLoading = false
    state.data = payload
    state.error = false
  },
  [mutationTypes.getArticleFailure](state) {
    state.isLoading = false
    state.data = null
    state.error = null
  },
  [mutationTypes.deleteArticleStart]() {},
  [mutationTypes.deleteArticleSuccess]() {},
  [mutationTypes.deleteArticleFailure]() {},
}

export const actionTypes = {
  getArticle: `${preffix} Get article`,
  deleteArticle: `${preffix} Delete article`,
}

const actions = {
  [actionTypes.getArticle](context, { slug }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.getArticleStart)
      articleApi
        .getArticle(slug)
        .then((article) => {
          context.commit(mutationTypes.getArticleSuccess, article)
          resolve(article)
        })
        .catch(() => {
          context.commit(mutationTypes.getArticleFailure)
        })
    })
  },
  [actionTypes.deleteArticle](context, { slug }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.deleteArticleStart)
      articleApi
        .deleteArticle(slug)
        .then(() => {
          context.commit(mutationTypes.deleteArticleSuccess)
          resolve()
        })
        .catch(() => {
          context.commit(mutationTypes.deleteArticleFailure)
        })
    })
  },
}

export default {
  state,
  mutations,
  actions,
}
