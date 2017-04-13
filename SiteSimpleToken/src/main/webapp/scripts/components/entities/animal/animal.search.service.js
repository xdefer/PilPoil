'use strict';

angular.module('pilpoilwebApp')
    .factory('AnimalSearch', function ($resource) {
        return $resource('api/_search/animals/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
