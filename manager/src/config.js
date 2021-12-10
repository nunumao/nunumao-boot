let config = {
    'DEBUG': false,
    'BASE_API': '/',
    'STATIC_PATH': '',
    'SYSTEM_NAME': 'Nunumao',
    'INTRO': '',
    'CLIENT_ID': '37z9c8hiCmKX5wpF',
    'CLIENT_SECRET': '9Qf0hd6XpGZ85tm1WkqcC7FMUElnNgib',
    'REFRESH_TOKEN': 'oauth2/token',
    'SESSION_SECRET': 'U2FsdGVkX18/DAktXYvKqX4YKCGIR92NIP07PJ4q0tE=',
    'SUCCESS_CODE': 200,
    'FAIL_CODE': 300
}

switch (process.env.NODE_ENV) {
    case 'development':
        // 开启调试模式
        config.DEBUG = true;
        // 测试环境接口地址
        config.BASE_API = 'http://localhost:9527/';
        // config.BASE_API = 'https://nunumao.365d1.com/';
        break;
}

config.STATIC_PATH = config.BASE_API + 'upload/';

export default config;