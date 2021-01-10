/**
 * Custom axios instance
 */

import axios from 'axios'

export default axios.create({
  baseURL: process.env.VUE_APP_API_BASE,
  timeout: 5 * 1000, // 5s
  headers: {
    // 'X-Custom-Header': 'foobar',
    // 'Authorization': true,
    'X-Requested-With': 'XMLHttpRequest'
  }
})
