let storedEmail = '';

function setEmail(email) {
  storedEmail = email;
}

function getEmail() {
  return storedEmail;
}

module.exports = {
  setEmail,
  getEmail,
};
