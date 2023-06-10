function generateVerificationCode() {
  const characters = '0123456789';
  const codeLength = 6;
  let verificationCode = '';

  for (let i = 0; i < codeLength; i++) {
    const randomIndex = Math.floor(Math.random() * characters.length);
    verificationCode += characters.charAt(randomIndex);
  }

  return verificationCode;
}


module.exports = generateVerificationCode;
