'use strict';

angular.module('pilpoilApp').factory('Breed', function ($resource, DateUtils) {
	return $resource('api/breeds/:id', {}, {
		'query': { method: 'GET', isArray: true},
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
            }
        },
        'update': { method:'PUT' }
    });
}).factory('BreedByAnimalType', function ($resource, DateUtils){
	return $resource('api/breeds/animalType/:id', {}, {
		'get' : { method: 'GET', isArray: true }
    });
});
