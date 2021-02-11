<template>
  <!-- <v-container>
    <v-row class="text-center"> -->
  <v-list>
    <v-list-item v-for="(p, i) in products" :key="i">
      <v-list-item-content>
        <v-list-item-content>
          {{ p.name }}
        </v-list-item-content>
      </v-list-item-content>

      <v-list-item-action>
        <div class="d-flex">
          <v-btn class="ml-2">
            <v-icon color="blue lighten-1">mdi-information</v-icon>
          </v-btn>
          <v-btn class="ml-2">
            <v-icon color="red">mdi-delete</v-icon>
          </v-btn>
        </div>
      </v-list-item-action>
    </v-list-item>
  </v-list>
  <!-- </v-row>
  </v-container> -->
</template>

<script>
export default {
  name: 'Products',

  data: () => ({
    products: [
      { name: 'Milk' },
      { name: 'Tea' },
      { name: 'Super puper product' },
    ],
  }),
}
</script>
