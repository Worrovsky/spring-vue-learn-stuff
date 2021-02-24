<template>
  <v-card class="mx-auto">
    <v-card-title>Sign In</v-card-title>
    <v-card-text>
      <v-text-field label="Name"></v-text-field>
      <v-text-field
        label="Password"
        :type="showPassword ? 'text' : 'password'"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        @click:append="showPassword = !showPassword"
      ></v-text-field>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="primary">Submit</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: 'AppSignIn',

  data() {
    return {
      showPassword: false,
    }
  },
}
</script>
