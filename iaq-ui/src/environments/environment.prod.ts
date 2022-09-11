// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const API_BASE_PATH = "https://www.botdojo.org"
const API_CONFIGS_BASE_PATH = `${API_BASE_PATH}/configs`;

const COGNITO_BASE_PATH = "https://iaq-ui.auth.us-east-1.amazoncognito.com";
const COGNITO_RESPONSE_TYPE = "token";
const COGNITO_CLIENT_ID = "1bfjtpglcdb3eincbigolgmu0c";
const COGNITO_REDIRECT_URL = "https://cmow5.github.io/indoor-air-quality-monitoring-system/login";
const COGNITO_SCOPE = "openid+aws.cognito.signin.user.admin";

export const environment = {
  production: true,
  API_BASE_PATH: API_BASE_PATH,

  // station api endpoints
  API_STATION_PATH: `${API_BASE_PATH}/station`,
  API_METRIC_PATH: `${API_BASE_PATH}/metrics`,

  // historic API endpoints
  buildHistoricUrl: function(stationId: string, metric: string) {
    return `${environment.API_STATION_PATH}/${stationId}/metric/${metric}/historic`;
  },


  // MQTT configs endpoint
  localMqtt: false,
  MQTT_CONFIGS_PATH: `${API_CONFIGS_BASE_PATH}/mqtt`,

  // COGNITO endpoints
  COGNITO_LOGIN_URL: `${COGNITO_BASE_PATH}/login?response_type=${COGNITO_RESPONSE_TYPE}&client_id=${COGNITO_CLIENT_ID}&redirect_uri=${COGNITO_REDIRECT_URL}&scope=${COGNITO_SCOPE}`,
  COGNITO_USER_INFO_URL: `${COGNITO_BASE_PATH}/oauth2/userInfo`,
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
