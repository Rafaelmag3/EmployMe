const userService = require('../service/service');

function createJobOffer(req, res) {
    const jobOffer = req.body;
    userService.createOffer(jobOffer, (error, result) => {
        if (error) {
            console.log('Error al crear el trabajo', error);
            res.status(500).send('Error al crear el trabajo');
        } else {
            res.status(200).json(jobOffer);
        }
    });
}

function getAllJobOffers(req, res) {
    userService.getAllOffers((error, result) => {
        if (error) {
            console.log('Error al obtener las ofertas de trabajo', error);
            res.status(500).send('Error al obtener las ofertas de trabajo');
        } else {
            res.status(200).json(result);
        }
    });
}

function getOfferById(req, res) {
    const { offerId } = req.params;
    userService.getOfferById(offerId, (error, result) => {
        if (error) {
            console.log('Error al obtener las ofertas de trabajo', error);
            res.status(500).send('Error al obtener las ofertas de trabajo');
        } else {
            res.status(200).json(result);
        }
    })
}

function deleteJobOffer(req, res) {
    const offerId = req.params.id;
    userService.deleteOffer(offerId, (error, result) => {
        if (error) {
            console.log('Error al eliminar la oferta de trabajo', error);
            res.status(500).send('Error al eliminar la oferta de trabajo');
        } else {
            res.status(200).json(result);
        }
    });
}

function updateJobOffer(req, res) {
    const offerId = req.params.id;
    const updatedOffer = req.body;
    userService.updateOffer(offerId, updatedOffer, (error, result) => {
        if (error) {
            console.log('Error al modificar la oferta de trabajo', error);
            res.status(500).send('Error al modificar la oferta de trabajo');
        } else {
            res.status(200).json(result);
        }
    });
}

module.exports = {
    createJobOffer,
    getAllJobOffers,
    getOfferById,
    deleteJobOffer,
    updateJobOffer
}