import axios from '@/api/axios'

const register = (credential) => {
  return axios.post('/users', { user: credential })
}

const signin = (credential) => {
  return axios.post('/users/login', { user: credential })
}

export default {
  register,
  signin,
}
