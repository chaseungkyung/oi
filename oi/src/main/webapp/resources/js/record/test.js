const crypto = require('crypto');
const OAuth = require('oauth-1.0a'); // OAuth 라이브러리 사용 권장

function generateOAuthSignature(consumerKey, consumerSecret, url) {
    // OAuth 파라미터 준비
    const oauth = {
        oauth_consumer_key: consumerKey,
        oauth_signature_method: 'HMAC-SHA1',
        oauth_timestamp: Math.floor(Date.now() / 1000).toString(),
        oauth_nonce: crypto.randomBytes(16).toString('hex'),
        oauth_version: '1.0'
    };

    // 파라미터 정렬 및 인코딩
    const sortedParams = Object.keys(oauth)
        .sort()
        .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(oauth[key])}`)
        .join('&');

    // 서명 베이스 문자열 생성
    const baseString = [
        'GET',
        encodeURIComponent(url),
        encodeURIComponent(sortedParams)
    ].join('&');

    // HMAC-SHA1 서명 생성
    const signingKey = `${encodeURIComponent(consumerSecret)}&`;
    const signature = crypto
        .createHmac('sha1', signingKey)
        .update(baseString)
        .digest('base64');

    return signature;
}

// 사용 예시
const consumerKey = 'your_consumer_key';
const consumerSecret = 'your_consumer_secret';
const url = 'https://platform.fatsecret.com/rest/foods/search/v1';

const oauthSignature = generateOAuthSignature(consumerKey, consumerSecret, url);
