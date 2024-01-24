const fs = require('fs');
module.exports = {
    devServer: {
        https: true,
        https: {
            key: fs.readFileSync('/etc/letsencrypt/live/capstone-6.shop/privkey.pem'),
            cert: fs.readFileSync('/etc/letsencrypt/live/capstone-6.shop/cert.pem'),
            ca: fs.readFileSync('/etc/letsencrypt/live/capstone-6.shop/chain.pem'),
        },
        hot: true,
        allowedHosts: "all",
    },
};