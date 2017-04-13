'use strict';

angular.module('pilpoilApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ad', {
                parent: 'entity',
                url: '/ads',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'pilpoilApp.ad.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ad/ads.html',
                        controller: 'AdController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ad');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ad.detail', {
                parent: 'entity',
                url: '/ad/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'pilpoilApp.ad.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ad/ad-detail.html',
                        controller: 'AdDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ad');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Ad', function($stateParams, Ad) {
                        return Ad.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ad.new', {
                parent: 'ad',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ad/ad-dialog.html',
                        controller: 'AdDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    recover: null,
                                    date: null,
                                    phone: null,
                                    email: null,
                                    adress: null,
                                    complement: null,
                                    postalCode: null,
                                    city: null,
                                    country: null,
                                    longitude: null,
                                    lattitude: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ad', null, { reload: true });
                    }, function() {
                        $state.go('ad');
                    })
                }]
            })
            .state('ad.edit', {
                parent: 'ad',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ad/ad-dialog.html',
                        controller: 'AdDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ad', function(Ad) {
                                return Ad.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ad', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('ad.delete', {
                parent: 'ad',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ad/ad-delete-dialog.html',
                        controller: 'AdDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Ad', function(Ad) {
                                return Ad.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ad', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
