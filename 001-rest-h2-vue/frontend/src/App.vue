<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center"></div>

      <v-spacer></v-spacer>
    </v-app-bar>

    <v-main>
      <Products />
    </v-main>
  </v-app>
</template>

<script>
import Products from './components/Products'

export default {
  name: 'App',

  components: {
    Products,
  },

  data: () => ({
    //
  }),
}
</script>
