'use strict';

angular.module('pilPoilApp')
    .factory('AnimalType', function ($resource, DateUtils) {
        return $resource('api/animalTypes/:id', {}, {
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
