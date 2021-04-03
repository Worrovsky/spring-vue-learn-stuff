import favoritesApi from '@/api/favorites'

const preffix = '[add to favorites]'

export const mutationTypes = {
  addToFavoritesStart: `${preffix} Add to favorites start`,
  addToFavoritesSuccess: `${preffix} Add to favorites success`,
  addToFavoritesFailure: `${preffix} Add to favorites failure`,
}

export const actionTypes = {
  addToFavorites: `${preffix} Add to favorites`,
}

const mutations = {
  [mutationTypes.addToFavoritesStart]() {},
  [mutationTypes.addToFavoritesSuccess]() {},
  [mutationTypes.addToFavoritesFailure]() {},
}

const actions = {
  [actionTypes.addToFavorites](context, { slug, isFavorited }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.addToFavoritesStart)

      const promise = !isFavorited
        ? favoritesApi.addToFavorites(slug)
        : favoritesApi.removeFromFavorites(slug)

      promise
        .then((article) => {
          context.commit(mutationTypes.addToFavoritesSuccess)
          resolve(article)
        })
        .catch(() => {
          context.commit(mutationTypes.addToFavoritesFailure)
        })
    })
  },
}

export default {
  mutations,
  actions,
}
