'use strict';

angular.module('pilpoilwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('adType', {
                parent: 'entity',
                url: '/adTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'pilpoilwebApp.adType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/adType/adTypes.html',
                        controller: 'AdTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('adType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('adType.detail', {
                parent: 'entity',
                url: '/adType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'pilpoilwebApp.adType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/adType/adType-detail.html',
                        controller: 'AdTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('adType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AdType', function($stateParams, AdType) {
                        return AdType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('adType.new', {
                parent: 'adType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/adType/adType-dialog.html',
                        controller: 'AdTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    label: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('adType', null, { reload: true });
                    }, function() {
                        $state.go('adType');
                    })
                }]
            })
            .state('adType.edit', {
                parent: 'adType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/adType/adType-dialog.html',
                        controller: 'AdTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AdType', function(AdType) {
                                return AdType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('adType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('adType.delete', {
                parent: 'adType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/adType/adType-delete-dialog.html',
                        controller: 'AdTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AdType', function(AdType) {
                                return AdType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('adType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
