<template>
  <v-app>
    <v-main>
      <app-navbar></app-navbar>

      <v-container>
        <v-row>
          <router-view></router-view>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import AppNavbar from '@/components/Navbar'

import { actionTypes } from '@/store/modules/auth'

export default {
  components: {
    AppNavbar,
  },

  mounted() {
    this.$store.dispatch(actionTypes.getCurrentUser)
  },
}
</script>
