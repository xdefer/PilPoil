'use strict';

angular.module('pilpoilwebApp')
    .factory('AdSearch', function ($resource) {
        return $resource('api/_search/ads/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
