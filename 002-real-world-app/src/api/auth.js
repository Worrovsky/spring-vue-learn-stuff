import axios from '@/api/axios'

const register = (credential) => {
  return axios.post('/users', { user: credential })
}

export default {
  register,
}
