// import axios from 'axios'

// const getUserInfo = (userId, userPw) => {
//   const reqData = {
//     'username': userId,
//     'password': userPw
//   }

//   let serverUrl = '//localhost:8080'

//   return axios.post(serverUrl + '/api/login', reqData, {
//     headers: {
//       'Content-type': 'application/json',
//       'X-Requested-With': 'XMLHttpRequest'
//     }
//   })
// }

// export default {
//   async doLogin(userId, userPw) {
//     try {
//       const getUserInfoPromise = getUserInfo(userId, userPw)
//       const [userInfoResponse] = await Promise.all([getUserInfoPromise])
//       if (userInfoResponse.data.length === 0) {
//         return 'notFound'
//       } else {
//         localStorage.setItem('user_token', userInfoResponse.data.user_token)
//         // localStorage.setItem('user_role', userInfoResponse.data.user_role)
//         return userInfoResponse
//       }
//     } catch (err) {
//       console.error(err)
//     }
//   }
// }