import axios from '@/api/axios'

const getUrl = (slug) => {
  return `/articles/${slug}/favorite`
}

const addToFavorites = (slug) => {
  return axios.post(getUrl(slug)).then((response) => response.data.article)
}

const removeFromFavorites = (slug) => {
  return axios.delete(getUrl(slug)).then((response) => response.data.article)
}

export default {
  addToFavorites,
  removeFromFavorites,
}
