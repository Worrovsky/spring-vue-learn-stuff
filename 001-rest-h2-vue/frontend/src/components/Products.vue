<template>
  <v-container>
    <v-row class="text-center">
      <v-list>
        <v-list-item v-for="(p, i) in products" :key="i">
          <v-list-item-content>
            <v-list-item-content>
              {{ p.name }}
            </v-list-item-content>
          </v-list-item-content>

          <v-list-item-action>
            <v-btn icon>
              <v-icon color="grey lighten-1">mdi-information</v-icon>
            </v-btn>
          </v-list-item-action>
        </v-list-item>
      </v-list>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Products',

  data: () => ({
    products: [{ name: 'Milk' }, { name: 'Tea' }],
  }),
}
</script>
