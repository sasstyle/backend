
from django.urls import path, include
from fitting.views import FittingAPIView, FittingListView, FittingFilteringListView

urlpatterns = [
    path('fittings/', FittingListView.as_view()), 
    path('fittings/<int:pk>/', FittingAPIView.as_view()),
    path('fittings/<int:productId>/', FittingFilteringListView.as_view()),
]
