import axios from '@/api/axios'

const getPopularTags = () => {
  const apiUrl = '/tags'
  return axios.get(apiUrl).then((response) => response.data.tags)
}

export default {
  getPopularTags,
}
