'use strict';

angular.module('pilpoilwebApp')
    .factory('Breed', function ($resource, DateUtils) {
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
    });
