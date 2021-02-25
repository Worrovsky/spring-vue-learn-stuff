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

export default {
  components: {
    AppNavbar,
  },
}
</script>
