// import axios from 'axios'

// const getUserInfo = (userId, userPw) => {
//   const reqData = {
//     'loginId': userId,
//     'password': userPw
//   }

//   let serverUrl = '//localhost:8080'

//   return axios.post(serverUrl + '/login', reqData, {
//     headers: {
//       'Content-type': 'application/json'
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