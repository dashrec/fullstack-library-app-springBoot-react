export const oktaConfig = {
  clientId: '0oa99lfuiljKTQnXD5d7',
  issuer: 'https://dev-02159638.okta.com/oauth2/default',
  redirectUri: 'https://localhost:3000/login/callback',
  scopes: ['openid', 'profile', 'email'],
  pkce: true,
  disableHttpsCheck: true,
}