'use strict';

angular.module('pilpoilwebApp')
    .factory('AnimalTypeSearch', function ($resource) {
        return $resource('api/_search/animalTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
