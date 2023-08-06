const userService = require('../service/service');

function getFavoriteByIdUser(req, res) {
    const { id_user } = req.params;
    userService.getFavoriteByIdUser(id_user, (error, result) => {
        if (error) {
            console.log('Error al obtener los favoritos', error);
            res.status(500).send('Error al obtener los favoritos');
        } else {
            res.status(200).json(result);
        }
    })
}
module.exports = {
    getFavoriteByIdUser
}