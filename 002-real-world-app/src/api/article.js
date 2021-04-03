import axios from '@/api/axios'

const getArticle = (slug) => {
  const apiUrl = `/articles/${slug}`
  return axios.get(apiUrl).then((response) => response.data.article)
}

const deleteArticle = (slug) => {
  const apiUrl = `/articles/${slug}`
  return axios.delete(apiUrl)
}

const createArticle = (articleInput) => {
  const apiUrl = '/articles'
  return axios
    .post(apiUrl, { article: articleInput })
    .then((response) => response.data.article)
}

const updateArticle = (slug, articleInput) => {
  const apiUrl = `/articles/${slug}`
  return axios
    .put(apiUrl, articleInput)
    .then((response) => response.data.article)
}
export default {
  getArticle,
  deleteArticle,
  createArticle,
  updateArticle,
}
