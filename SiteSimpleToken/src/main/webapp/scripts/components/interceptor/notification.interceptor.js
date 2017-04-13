 'use strict';

angular.module('pilpoilwebApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-pilpoilwebApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-pilpoilwebApp-params')});
                }
                return response;
            }
        };
    });
