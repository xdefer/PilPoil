'use strict';

angular.module('pilpoilwebApp')
    .factory('BreedSearch', function ($resource) {
        return $resource('api/_search/breeds/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
