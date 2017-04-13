 'use strict';

angular.module('pilPoilApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-pilPoilApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-pilPoilApp-params')});
                }
                return response;
            }
        };
    });
