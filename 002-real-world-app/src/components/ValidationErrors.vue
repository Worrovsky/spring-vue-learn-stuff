<template>
  <div>
    <v-list>
      <v-subheader color="error">ERRORS:</v-subheader>
      <v-list-item v-for="(message, i) in errorMessages" :key="i">
        <v-list-item-icon>
          <v-icon color="error">mdi-alert</v-icon>
        </v-list-item-icon>
        <v-list-item-content class="error--text">{{
          message
        }}</v-list-item-content>
      </v-list-item>
    </v-list>
  </div>
</template>

<script>
export default {
  name: 'AppValidationErrors',
  props: {
    validationErrors: {
      type: Object,
      required: true,
    },
  },
  computed: {
    errorMessages() {
      return Object.keys(this.validationErrors).map((name) => {
        const message = this.validationErrors[name].join(', ')
        return `${name}: ${message}`
      })
    },
  },
}
</script>
