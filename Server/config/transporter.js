const nodemailer = require('nodemailer');

function createTransporter() {
    let transporter = nodemailer.createTransport({
        host: "smtp.gmail.com",
        port: 465,
        secure: true,
        auth: {
            user: "employmework@gmail.com",
            pass: "ngiffloeevxtatdx",
        },
    });

    return transporter;
}



module.exports = {
    createTransporter,
};
