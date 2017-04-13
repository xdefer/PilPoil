'use strict';

angular.module('pilpoilwebApp')
    .factory('AdTypeSearch', function ($resource) {
        return $resource('api/_search/adTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
