import reserve as reserve

from django.db import models

class Fitting(models.Model):
    userId = models.CharField(max_length=100, default='fa9f83fc-934e-408a-9875-4092e2872694')
    productId = models.FloatField()

    image = models.ImageField()
    desc = models.CharField(max_length=100, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateField(auto_now=True)

