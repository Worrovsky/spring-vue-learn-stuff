import axios from '@/api/axios'

const getUserProfile = (slug) => {
  const apiUrl = `/profiles/${slug}`
  return axios.get(apiUrl).then((response) => response.data.profile)
}

export default {
  getUserProfile,
}
